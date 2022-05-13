import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Actor } from 'src/app/model/actor';
import { Comment } from 'src/app/model/comment';
import { ActorService } from 'src/app/service/actor.service';
import { CommentCreationData } from 'src/app/service/class/comment-creation-data';
import { AccessService, UserType } from 'src/app/service/helpers/access.service';
import { PopUpService } from 'src/app/service/helpers/pop-up.service';
import { LessorService } from 'src/app/service/lessor.service';
import { TenantService } from 'src/app/service/tenant.service';

@Component({
  selector: 'profile-data',
  templateUrl: './profile-data.component.html',
  styleUrls: ['./profile-data.component.css']
})
export class ProfileDataComponent {

  private _actor: Actor = new Actor;
  get actor(): Actor { return this._actor; }
  @Input() set actor(actor: Actor) {
    let errorHandle = (e: any) => {
      this.popUpService.Handle(e.error);
      this.router.navigate(["/"]);
    }
    this._actor = actor;
    if(actor.type === "lessor"){
      this.lessorService.listComments(actor.id).then(comments => this.comments = comments).catch(errorHandle)
    }else if(actor.type === "tenant"){
      this.tenantService.listComments(actor.id).then(comments => this.comments = comments).catch(errorHandle)
    }
  }

  @Input() mySelf: boolean = false;
  @Output() onEdit = new EventEmitter<void>();

  comments: Comment[] = [];
  newComment: CommentCreationData = new CommentCreationData;

  myTipe: UserType = this.accessService.getUserType();
  myActor: Actor = new Actor;

  constructor(
    private readonly translator: TranslateService,
    private readonly router: Router,
    private readonly popUpService: PopUpService,
    private readonly accessService: AccessService,
    private readonly actorService: ActorService,
    private readonly lessorService: LessorService,
    private readonly tenantService: TenantService
  ) {
    const myId = accessService.getUserId();
    if(myId !== null)
      this.actorService.get(myId).then(actor => this.myActor = actor);
  }

  signOut(){
    this.accessService.unsetJwt();
    location.href = "/";
  }

  sendComment(){
    this.newComment.title = this.newComment.title.trim();
    if(this.newComment.title.length === 0){
      this.popUpService.Error(this.translator.instant("DIALOG.SENDCOMMENT.EMPTYTITLE"));
      return;
    }

    this.newComment.text = this.newComment.text.trim();
    if(this.newComment.text.length === 0){
      this.popUpService.Error(this.translator.instant("DIALOG.SENDCOMMENT.EMPTYTEXT"));
      return;
    }

    let handle = () => {
      this.comments.unshift({...this.newComment, date: new Date(), author: this.myActor} as Comment);
      this.newComment = new CommentCreationData;
    }

    if(this.actor.type === "lessor"){
      this.lessorService.createComment(this.actor.id, this.newComment).then(handle);
    }else if(this.actor.type === "tenant"){
      this.tenantService.createComment(this.actor.id, this.newComment).then(handle);
    }
  }
}
