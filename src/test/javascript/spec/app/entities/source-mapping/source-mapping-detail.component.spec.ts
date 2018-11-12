/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceMappingDetailComponent } from 'app/entities/source-mapping/source-mapping-detail.component';
import { SourceMapping } from 'app/shared/model/source-mapping.model';

describe('Component Tests', () => {
    describe('SourceMapping Management Detail Component', () => {
        let comp: SourceMappingDetailComponent;
        let fixture: ComponentFixture<SourceMappingDetailComponent>;
        const route = ({ data: of({ sourceMapping: new SourceMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SourceMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SourceMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sourceMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
